INSERT INTO activities (emoji_id, name)
VALUES
    (1, 'Running'),
    (2, 'Yoga'),
    (3, 'Swimming'),
    (4, 'Cycling'),
    (5, 'Hiking'),
    (6, 'Dancing'),
    (7, 'Meditation'),
    (8, 'Weightlifting'),
    (9, 'Boxing'),
    (10, 'Pilates')
ON CONFLICT (name)
DO NOTHING;