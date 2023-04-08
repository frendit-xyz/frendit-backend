CREATE OR REPLACE FUNCTION rank_posts() RETURNS TABLE (
  id INTEGER,
  rank INTEGER
) AS $$
BEGIN
  RETURN QUERY
    SELECT id, ROW_NUMBER() OVER (ORDER BY rank DESC)
    FROM (
      SELECT id,
             COALESCE(content_weight, 0) +
             COALESCE(bg_text_weight, 0) +
             COALESCE(bg_color_weight, 0) +
             COALESCE(video_link_weight, 0) +
             COALESCE(gif_link_weight, 0) +
             COALESCE(links_weight, 0) +
             COALESCE(activity_details_weight, 0) +
             COALESCE(activity_id_weight, 0) +
             COALESCE(author_id_weight, 0) +
             COALESCE(location_id_weight, 0) +
             COALESCE(mood_id_weight, 0) +
             COALESCE(category_id_weight, 0) +
             COALESCE(status_weight, 0) +
             COALESCE(can_react_weight, 0) +
             COALESCE(can_comment_weight, 0) +
             COALESCE(can_vote_weight, 0) +
             COALESCE(boost_weight, 0) AS rank,
             RANDOM() AS rand
      FROM (
        SELECT *,
               CASE WHEN content IS NOT NULL THEN 10 ELSE 0 END AS content_weight,
               CASE WHEN bg_text IS NOT NULL THEN 5 ELSE 0 END AS bg_text_weight,
               CASE WHEN bg_color IS NOT NULL THEN 5 ELSE 0 END AS bg_color_weight,
               CASE WHEN video_link IS NOT NULL THEN 8 ELSE 0 END AS video_link_weight,
               CASE WHEN gif_link IS NOT NULL THEN 8 ELSE 0 END AS gif_link_weight,
               CASE WHEN array_length(links, 1) IS NOT NULL THEN 5 ELSE 0 END AS links_weight,
               CASE WHEN activity_details IS NOT NULL THEN 5 ELSE 0 END AS activity_details_weight,
               CASE WHEN activity_id IS NOT NULL THEN 10 ELSE 0 END AS activity_id_weight,
               CASE WHEN author_id IS NOT NULL THEN 10 ELSE 0 END AS author_id_weight,
               CASE WHEN location_id IS NOT NULL THEN 5 ELSE 0 END AS location_id_weight,
               CASE WHEN mood_id IS NOT NULL THEN 5 ELSE 0 END AS mood_id_weight,
               CASE WHEN category_id IS NOT NULL THEN 5 ELSE 0 END AS category_id_weight,
               CASE WHEN status = 'PUBLISHED' THEN 5 ELSE 0 END AS status_weight,
               CASE WHEN can_react THEN 5 ELSE 0 END AS can_react_weight,
               CASE WHEN can_comment THEN 5 ELSE 0 END AS can_comment_weight,
               CASE WHEN can_vote THEN 5 ELSE 0 END AS can_vote_weight,
               CASE WHEN boost > 1 THEN boost - 1 ELSE 0 END AS boost_weight
        FROM posts
      ) subquery
    ) subquery2
    ORDER BY rand;
END;
$$ LANGUAGE plpgsql;

--CREATE OR REPLACE FUNCTION rank_posts()
--RETURNS TABLE(id INTEGER, rank NUMERIC) AS $$
--BEGIN
--    RETURN QUERY
--    SELECT id, (
--        (length(content) / 1500.0) * 0.3 +
--        (length(bg_text) / 250.0) * 0.1 +
--        (length(bg_color) / 20.0) * 0.1 +
--        (CASE WHEN video_link IS NOT NULL THEN 0.2 ELSE 0 END) +
--        (CASE WHEN gif_link IS NOT NULL THEN 0.2 ELSE 0 END) +
--        (array_length(links, 1) / 5.0) * 0.1 +
--        (CASE WHEN mood_id IS NOT NULL THEN 0.1 ELSE 0 END) +
--        (CASE WHEN location_id IS NOT NULL THEN 0.1 ELSE 0 END) +
--        (CASE WHEN category_id IS NOT NULL THEN 0.1 ELSE 0 END) +
--        (CASE WHEN status = 'PUBLISHED' THEN 0.2 ELSE 0 END) +
--        (CASE WHEN status = 'DRAFT' THEN -0.1 ELSE 0 END) +
--        (CASE WHEN status = 'TRASHED' THEN -0.5 ELSE 0 END) +
--        (CASE WHEN can_react THEN 0.1 ELSE 0 END) +
--        (CASE WHEN can_comment THEN 0.1 ELSE 0 END) +
--        (CASE WHEN can_vote THEN 0.1 ELSE 0 END) +
--        (boost / 10.0) * 0.2
--    ) + random() AS rank
--    FROM posts;
--END;
--$$ LANGUAGE PLPGSQL;

--CREATE OR REPLACE FUNCTION rank_posts()
--RETURNS TABLE (id INTEGER, rank NUMERIC) AS $$
--BEGIN
--    RETURN QUERY
--    SELECT
--        id,
--        (
--            (CASE WHEN status = 'PUBLISHED' THEN 1 ELSE 0 END) * 1000 -- published posts get a higher base score
--            + COALESCE(boost, 1) * 100 -- the boost column also affects the rank calculation
--            + (CASE WHEN can_react THEN 1 ELSE 0 END) * 10 -- can_react, can_comment, and can_vote each contribute a small amount
--            + (CASE WHEN can_comment THEN 1 ELSE 0 END) * 5
--            + (CASE WHEN can_vote THEN 1 ELSE 0 END) * 2
--            + random() -- add a random value to the rank calculation
--        ) AS rank
--    FROM posts
--    WHERE status = 'PUBLISHED' -- only rank published posts
--    ORDER BY rank DESC;
--END;
--$$ LANGUAGE plpgsql;



--SELECT id, content, publish_at, rank
--FROM posts
--INNER JOIN rank_posts() ON posts.id = rank_posts().id
--ORDER BY rank DESC;

--SELECT id, rank
--FROM rank_posts()
--ORDER BY rank DESC;
