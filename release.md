## Release command

We @foconis use this command to release.

    # Release from the sub directory
    cd ebean-agent
    # push the artifacts to foconis repository
    mvn release:prepare release:perform -Pfoconis
    # manually checkout the commit with release tag (e.g. 13.10.3-FOC1)
    # deployment to github packages (your local settings.xml has to contain the PAT (personal access token) for github-release
    mvn deploy -Pgithub

