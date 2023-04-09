# POST RANKING SQL

```
CREATE OR REPLACE FUNCTION calculate_post_weight()
RETURNS TABLE (
id INTEGER,
content TEXT,
bg_text TEXT,
bg_color TEXT,
video_link TEXT,
gif_link TEXT,
links TEXT[],
activity_details TEXT,
activity_id INTEGER,
author_id INTEGER,
location_id INTEGER,
mood_id INTEGER,
category_id INTEGER,
status TEXT,
can_react BOOLEAN,
can_comment BOOLEAN,
can_vote BOOLEAN,
boost INTEGER,
total_weight INTEGER
) AS $$
BEGIN
RETURN QUERY
SELECT
p.id,
p.content,
p.bg_text,
p.bg_color,
p.video_link,
p.gif_link,
p.links,
p.activity_details,
p.activity_id,
p.author_id,
p.location_id,
p.mood_id,
p.category_id,
p.status,
p.can_react,
p.can_comment,
p.can_vote,
p.boost,
(
CASE WHEN p.content IS NOT NULL THEN 10 ELSE 0 END +
CASE WHEN p.bg_text IS NOT NULL THEN 5 ELSE 0 END +
CASE WHEN p.bg_color IS NOT NULL THEN 5 ELSE 0 END +
CASE WHEN p.video_link IS NOT NULL THEN 8 ELSE 0 END +
CASE WHEN p.gif_link IS NOT NULL THEN 8 ELSE 0 END +
CASE WHEN array_length(p.links, 1) IS NOT NULL THEN 5 ELSE 0 END +
CASE WHEN p.activity_details IS NOT NULL THEN 5 ELSE 0 END +
CASE WHEN p.activity_id IS NOT NULL THEN 10 ELSE 0 END +
CASE WHEN p.author_id IS NOT NULL THEN 10 ELSE 0 END +
CASE WHEN p.location_id IS NOT NULL THEN 5 ELSE 0 END +
CASE WHEN p.mood_id IS NOT NULL THEN 5 ELSE 0 END +
CASE WHEN p.category_id IS NOT NULL THEN 5 ELSE 0 END +
CASE WHEN p.status = 'PUBLISHED' THEN 5 ELSE 0 END +
CASE WHEN p.can_react THEN 5 ELSE 0 END +
CASE WHEN p.can_comment THEN 5 ELSE 0 END +
CASE WHEN p.can_vote THEN 5 ELSE 0 END +
CASE WHEN p.boost > 1 THEN p.boost - 1 ELSE 0 END
) AS total_weight
FROM posts p
ORDER BY p.id DESC
LIMIT 100;
END;
$$ LANGUAGE plpgsql;

--SELECT *
--FROM calculate_post_weight()
--ORDER BY total_weight * (RANDOM() + 0.5);
```
