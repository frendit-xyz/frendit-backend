INSERT INTO categories (slug, title)
VALUES
    ('technology', 'Technology'),
    ('sports', 'Sports'),
    ('food', 'Food'),
    ('fashion', 'Fashion'),
    ('travel', 'Travel'),
    ('health', 'Health'),
    ('music', 'Music'),
    ('books', 'Books'),
    ('movies', 'Movies'),
    ('art', 'Art')
ON CONFLICT (slug)
DO NOTHING;