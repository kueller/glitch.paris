#!/bin/bash

ORIGINAL_DIR=$(pwd)

cd "$(dirname "$0")"

pipenv --quiet run python build_shows.py $1

cd "$ORIGINAL_DIR"
