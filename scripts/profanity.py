import sys
import profanity_check

# Get the text value from the command line argument
text = sys.argv[1]

# Calculate the profanity score of the post
profanity_score = profanity_check.predict([text])[0]

# Print the profanity score
print(profanity_score)