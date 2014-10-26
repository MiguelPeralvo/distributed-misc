#!/bin/bash

if [[ "$USER" != "root" ]]; then
    echo "please run as: sudo $0"
    exit 1
fi

image_list=("spark-master:1.1.0" "spark-worker:1.1.0" "spark-shell:1.1.0" "spark-shell-py:1.1.0" "shark-master:0.8.0" "shark-worker:0.8.0" "shark-shell:0.8.0" )

IMAGE_PREFIX="anantasty/"

# NOTE: the order matters but this is the right one
for i in ${image_list[@]}; do
	image=$(echo $i | awk -F ":" '{print $1}')
        version=$(echo $i | awk -F ":" '{print $2}')
        echo docker tag ${i} ${IMAGE_PREFIX}${image}
	docker tag ${i} ${IMAGE_PREFIX}${image}:${version}
done
