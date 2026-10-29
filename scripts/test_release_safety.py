"""Regression protection for MobDrops CI: existing releases are immutable."""
import re
import unittest
from pathlib import Path

WORKFLOW = Path(__file__).resolve().parents[1] / ".github" / "workflows" / "build.yml"


def audit_readonly_build(text: str) -> None:
    """Keep regular push/PR/dispatch builds read-only and retain raw JARs."""
    if not re.search(r"(?m)^permissions:\s*\n  contents: read\s*$", text):
        raise ValueError("CI must grant read-only repository contents")
    if re.search(r"(?im)^\s*contents:\s*write\b", text):
        raise ValueError("CI must never grant release-writing credentials")
    if re.search(r"(?i)\bgh\s+release\s+(?:upload|create|delete|edit)\b|--clobber", text):
        raise ValueError("An ordinary build may not create or mutate release assets")
    if re.search(r"(?im)^\s*-\s*name:\s*publish\b", text):
        raise ValueError("The shared build must not contain a publishing step")
    if not re.search(r"(?m)^\s*-\s*name:\s*Upload raw JAR artifact\s*$", text):
        raise ValueError("Raw JAR artifact upload must remain available")
    for required in (
        "archive: false",
        "if-no-files-found: error",
        'SOURCE="target/${OUTPUT_NAME}"',
        'JAR="dist/${OUTPUT_NAME}"',
        'java-version: \'25\'',
        "Run MobDrops unit tests with MockBukkit 26.2",
        "Compile against Slimefun Legacy / Paper 1.21.11",
        "Compile against Slimefun Legacy / Paper 26.2",
        "Compile against Slimefun Legacy / Paper 26.3 alpha",
    ):
        if required not in text:
            raise ValueError(f"Required build and JAR evidence removed: {required}")


class ReleaseSafetyTest(unittest.TestCase):
    def test_real_build_workflow_cannot_publish_existing_versions(self):
        audit_readonly_build(WORKFLOW.read_text(encoding="utf-8"))

    def test_fails_closed_if_release_writes_or_clobber_return(self):
        original = WORKFLOW.read_text(encoding="utf-8")
        for token in (
            "contents: write",
            "gh release upload v1.0.4 candidate.jar",
            "gh release create v1.0.4 candidate.jar",
            "--clobber",
            "- name: Publish raw JAR release asset",
        ):
            with self.subTest(token=token):
                with self.assertRaises(ValueError):
                    audit_readonly_build(original + "\n" + token + "\n")

    def test_fails_closed_if_raw_jar_artifact_or_read_only_token_disappears(self):
        original = WORKFLOW.read_text(encoding="utf-8")
        for candidate in (
            original.replace("contents: read", "contents: write"),
            original.replace("archive: false", "archive: true"),
            original.replace("Upload raw JAR artifact", "Upload only source archive"),
        ):
            with self.subTest(candidate=candidate[-80:]):
                with self.assertRaises(ValueError):
                    audit_readonly_build(candidate)


if __name__ == "__main__":
    unittest.main()
