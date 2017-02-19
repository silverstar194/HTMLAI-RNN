##gathers up different files types from sub dir. Change filetypes and dir names as needed
#!/bin/bash
a=0
find ./*/ -name '*.jpg' -print0 | while IFS= read -r -d $'\0' line; do
    echo "$line"
    cp $line allJPG
    NAME=$(basename $line)
    mv allJPG/$NAME allJPG/$a-$NAME
    a=$((a+1))
done