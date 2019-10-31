#!/bin/bash

mvn --quiet validate release:branch -DupdateBranchVersions=true -DbranchName=\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion} -DdevelopmentVersion=\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}.\${parsedVersion.nextIncrementalVersion}-SNAPSHOT -DreleaseVersion=\${parsedVersion.majorVersion}.\${parsedVersion.nextMinorVersion}.\${parsedVersion.nextIncrementalVersion}
#mvn --quiet build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} -DgenerateBackupPoms=false -DprocessAllModules versions:commit
#mvn --quiet scm:add -Dmessage="maven-scm" -Dincludes=\${base.dir}/pom.xml -DpushChanges=false
#mvn --quiet scm:checkin -Dmessage="[maven-scm] prepare release"
