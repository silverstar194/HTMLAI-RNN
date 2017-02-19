# HTMLAI-RNN
##Goal:
Train a RNN (Recurrent Neural Network) to generate valid HTML and CSS templates for websites based on character by character training.

I was inspired to try this by:
https://www.youtube.com/watch?v=iX5V1WpxxkY&t=2881s

Excellent course for neural nets in general.

##Idea behind RNN:
Unlike a vanilla neural net that takes a input vector and returns an output vector, a RNN feeds the output vector back upon itself to minimize a cost function across multiple training examples.

Learn more by starting here:
http://www.wildml.com/2015/09/recurrent-neural-networks-tutorial-part-1-introduction-to-rnns/

This sums up the basic idea:

![alt tag](http://d3kbpzbmcynnmx.cloudfront.net/wp-content/uploads/2015/09/bidirectional-rnn-300x196.png)

Note the "post-input training inputs" from the layers output aka unfolding as refered to in above article.


##HTML and CSS RNN Model
128 vector input (ascii character range)

2 hidden layers

256 neurons per layer

128 vector output of probabilites (ascii character range)

output next char predicted in sequence w/ softmax classifier

##Traning Data
Gathered ~5000 html website templates from http://www.free-css.com/ and another template site (I can't find again as I write this) via script (mostly curl). Had to be a little tricky with http://www.free-css.com/ as downloads are redirected by javascript link. Avoided this by breaking out a bash loop and opening chrome tabs and killing processes post javascript.

HTML and CSS was stripped from directories, appended with a tagger marker and concatenated into one training file.
Once concatencated all non-ascii characters were stripped out.

##Training
Epoch 50

Min-Batches of 50

I trained on AWS p2.xlarge instances with GPUs (NVIDIA K80 GPUs). Even with the GPUs I had to break full ~5000 website dataset into half to complete training in a reasonble amount of time (~6 hours per model). I had one model for HTML and one for CSS.

Used AMI image: ami-c79b7eac (See https://github.com/brotchie/torch-ubuntu-gpu-ec2-install)
Also install dependencies specified by torch-rnn

##Post-Processing
#HTML Doc Cutting
For each template I generated more text than was generally in a html file and cut the file to proper length based on appened starting tag, "<!DOCTYPE ", and "</htlm>".

#CSS Insertion
I wanted to be sure that all the generated templates were able to reference a CSS file for styling, thus post generation I inserted a CSS style link into each document.

#Image Insertion
The generated HTML had vaild img tags and used past image links to generate "image like links" but they would not be able to link to any actully images. I went through and replaced each link with a link to a vaild image randomly pulled from the training set images.

#Commenting
The AI tended not to close comments which lead the html to not display at all after the failure to close. I stripped out comments to prevent this.

#Single Quote Replacement
The AI tended to not close quotes properly which lead to following html to be unusable. Replaceing all single quotes with double quotes greatly eliminated this.

##Results
(still to write)

##Challenges
-Training Time

-AWS learning curve

-Depth vs layer side tradeoffs

##Future Improvement
Use full training set

Scale network to 512 neurons per layer and 3 layers to capture more subtle features

Isolate html files per type:

-index.html

-contact.html

-etc

Better Image Selection

