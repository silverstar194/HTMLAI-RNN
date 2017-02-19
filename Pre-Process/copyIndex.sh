#!/bin/bash
a=0
find ./*/ -name index.html -print0 | while IFS= read -r -d $'\0' line; do
    echo "$line"
    cp $line index
    mv index/index.html index/index-$a.html
    a=$((a+1))
done