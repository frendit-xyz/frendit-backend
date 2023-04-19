INSERT INTO emojis (code, name, shorthand, css_class)
VALUES
    (':smile:', 'Smile', ':)', 'emoji-smile'),
    (':heart:', 'Heart', '<3', 'emoji-heart'),
    (':rocket:', 'Rocket', ':rocket:', 'emoji-rocket'),
    (':sunglasses:', 'Sunglasses', ':sunglasses:', 'emoji-sunglasses'),
    (':pizza:', 'Pizza', ':pizza:', 'emoji-pizza'),
    (':dog:', 'Dog', ':dog:', 'emoji-dog'),
    (':money:', 'Money', ':money:', 'emoji-money'),
    (':sun:', 'Sun', ':sun:', 'emoji-sun'),
    (':phone:', 'Phone', ':phone:', 'emoji-phone'),
    (':computer:', 'Computer', ':computer:', 'emoji-computer')
ON CONFLICT (code)
DO NOTHING;