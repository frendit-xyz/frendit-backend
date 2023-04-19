INSERT INTO moods (emoji_id, name)
VALUES
    (1, 'Happy'),
    (2, 'Sad'),
    (3, 'Angry'),
    (4, 'Calm'),
    (5, 'Excited'),
    (6, 'Bored'),
    (7, 'Stressed'),
    (8, 'Relaxed'),
    (9, 'In Love'),
    (10, 'Tired')
ON CONFLICT (name)
DO NOTHING;