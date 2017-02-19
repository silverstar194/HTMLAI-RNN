# HTMLAI-RNN
##Goal:
Train a RNN (Recurrent Neural Network) to generate valid HTML and CSS for website based on charactor by charactor training.

I was inspired to try this by:
https://www.youtube.com/watch?v=iX5V1WpxxkY&t=2881s

Excellent course for neural nets in general.

##Idea behind RNN:
Unlike a vanilla neural net that takes a input vector and returns an input vector a RNN feeds the input vector back upon itself to minimize a cost function across multiple inputs.

Learn more by starting here:
http://www.wildml.com/2015/09/recurrent-neural-networks-tutorial-part-1-introduction-to-rnns/

This sums up the basic idea:

![alt tag](http://d3kbpzbmcynnmx.cloudfront.net/wp-content/uploads/2015/09/bidirectional-rnn-300x196.png)

Note the post-input traning inputs from the layers output aka unfolding as refered to in above article.


##HTML and CSS RNN Model
128 vector ascii input vector

2 hidden layers

256 neurons per layer

128 vector input (ascii character range)

output next char predicted in sequence w/ softmax classifier

##Traning Data
Gathered ~5000 html website templates from http://www.free-css.com/ and another template site (I can't find it again as I write this) via script (mostly curl). Had to be a little tricky with http://www.free-css.com/ as downloads are redirected by javascript link. Avoided this by breaking out a bash loop and opening chrome tabs and killing processes post javascript.

HTML and CSS was stripped from directories, appened with a tagger mark and concatinated into one training file.
Once concatincated all non-ascci characters were stripped out.

##Training
