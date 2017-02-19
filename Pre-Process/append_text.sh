##append text to end of file for tagging after training
#!/bin/bash

for f in *.js ; 
do echo "<!--####-->" >> $f
	#echo '<!--%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%-->' | cat - $f > temp && mv temp $f
done

