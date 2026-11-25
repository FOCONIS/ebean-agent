## Bamboo: Ebean Agent

- Repository: `FOCONIS/ebean-agent`, branch `master`.
- Build requirements: JDK 21 and Maven 3.9.x.
- Working directory: repository root.
- Build task: `mvn -B -ntp clean verify`.
- JUnit reports: `**/target/surefire-reports/TEST-*.xml`.
- Build artifacts: `ebean-agent/target/ebean-agent-*.jar` (agent and sources).

## Release

Release only on demand from a clean checkout of `master`. The release job
needs SCM write access for the release commit and tag, and Maven settings
with deployment credentials for `favf-sonatype-nexus-releases`. Keep these
credentials in Bamboo, not in the repository.

Run both tasks from the repository root:

```sh
mvn -B -ntp release:prepare
mvn -B -ntp release:perform
```

The first development version is `18.7.0-FOC1-SNAPSHOT`. The Maven release
plugin tags the release as `ebean-agent-18.7.0-FOC1`. The release publishes
the reactor POM and the agent (including sources); the test module is not deployed.
The verification plan must not deploy snapshots to the release repository.

To additionally publish an existing release to GitHub Packages, check out
its tag and run `mvn -B -ntp deploy -Pgithub` from the root. Configure
credentials for `github-release` in Maven settings.
