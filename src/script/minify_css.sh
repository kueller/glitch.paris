#!/bin/bash


SCRIPTS=(
    "glitch.css glitch.min.css"
)


PROJECT_STATIC=$1


minify () {
    local big_css="$1"
    local min_css="$2"

    echo "Running minify on ${big_css}..."

    lightningcss \
        --minify "${PROJECT_STATIC}/style/${big_css}" \
        --output-file "${PROJECT_STATIC}/style/${min_css}"

    echo "Wrote ${min_css} from ${big_css}."
    echo "Done."

}


for params in "${SCRIPTS[@]}"; do
    minify $params
done