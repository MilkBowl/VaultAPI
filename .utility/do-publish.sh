#!/bin/bash
current_dir=`pwd`

if [[ "$TRAVIS_REPO_SLUG" != "MilkBowl/VaultAPI" || "$TRAVIS_PULL_REQUEST" == "true" || "$TRAVIS_BRANCH" != "master" ]]
then
        echo 'Travis can only publish docs for release builds.'
        return 0
fi

mvn clean javadoc:javadoc javadoc:jar deploy --settings .utility/settings.xml

# Get to the Travis build directory, configure git and clone the repo
cd $HOME
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/MilkBowl/VaultAPI gh-pages > /dev/null

# Commit and Push the Changes
cd gh-pages
git rm -rf *
cp -Rfv $current_dir/target/javadoc-latest/* ./
git add -f .
git commit -m "Latest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
git push -fq origin gh-pages > /dev/null
