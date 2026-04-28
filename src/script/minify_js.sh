#!/bin/bash


SCRIPTS=(
    "photo.js photo.bundle.js photo.min.js"
)


PROJECT_STATIC=$1


minify () {
    local big_js_file="$1"
    local bundle_js_file="$2"
    local min_js_file="$3"
    local extern_lib="$4"

    local external_opt=""

    echo Rolling up scripts for "${big_js_file}"

    if [[ -n "$extern_lib" ]]; then 
        external_opt="--external $extern_lib"
    fi

    rollup ${PROJECT_STATIC}/js/${big_js_file} \
        --silent \
        "${extern_opt}" \
        --file ${PROJECT_STATIC}/js/dist/${bundle_js_file} \
        2>&1

    local status=$?

    if [ $status -ne 0 ]; then
        echo "Rollup failed with status: ${status}"
        exit $status
    fi

    echo "Wrote ${bundle_js_file} from ${big_js_file}."

    echo Minifying "$big_js_file" from "$bundle_js_file"

    terser ${PROJECT_STATIC}/js/dist/${bundle_js_file} \
        --mangle \
        --compress \
        --module \
        --output ${PROJECT_STATIC}/js/dist/${min_js_file}

    echo "Done."
}


for params in "${SCRIPTS[@]}"; do
    minify $params
done