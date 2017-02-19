###general proccesing steps and commands so I don't forget them
## unip all zip files
unzip "*.zip"

## delete all zip files in dir
find . -name "*.zip" -exec rm "{}" \;

## move all zip files
find . -name "*.zip" -exec mv "{}" ./zipfiles \;


##unzip all files each to seperate directory
find -name '*.zip' -exec sh -c 'unzip -d "${1%.*}" "$1"' _ {} \;

##append all with newlines
for f in *.html; do (cat "${f}"; echo) >> finalfileHTMLT.txt; done
