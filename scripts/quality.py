import sys
from textblob import TextBlob

# Get the text value from the command line argument
text = sys.argv[1]

# Create a TextBlob object
blob = TextBlob(text)

# Calculate the polarity score of the text
polarity_score = blob.sentiment.polarity

# Scale the polarity score to a quality score between 0 and 100
quality_score = (polarity_score + 1) / 2 * 100

# Print the quality score
print(round(quality_score, 2))
