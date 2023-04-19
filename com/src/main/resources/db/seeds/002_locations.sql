INSERT INTO locations (lon, lat, name, address, thumbnail)
VALUES
    (-79.3849, 43.6529, 'CN Tower', '301 Front St W, Toronto, ON M5V 2T6, Canada', 'https://www.example.com/images/cn_tower.jpg'),
    (-73.9857, 40.7484, 'Empire State Building', '20 W 34th St, New York, NY 10001, USA', 'https://www.example.com/images/empire_state_building.jpg'),
    (-118.2437, 34.0522, 'Griffith Observatory', '2800 E Observatory Rd, Los Angeles, CA 90027, USA', 'https://www.example.com/images/griffith_observatory.jpg'),
    (-0.1278, 51.5074, 'Big Ben', 'Westminster, London SW1A 0AA, United Kingdom', 'https://www.example.com/images/big_ben.jpg'),
    (139.8395, 35.6528, 'Tokyo Tower', '4 Chome-2-8 Shibakoen, Minato City, Tokyo 105-0011, Japan', 'https://www.example.com/images/tokyo_tower.jpg'),
    (-77.0369, 38.9072, 'Washington Monument', '2 15th St NW, Washington, DC 20007, USA', 'https://www.example.com/images/washington_monument.jpg'),
    (-123.1207, 49.2827, 'Capilano Suspension Bridge', '3735 Capilano Rd, North Vancouver, BC V7R 4J1, Canada', 'https://www.example.com/images/capilano_bridge.jpg'),
    (-0.1181, 51.5094, 'The London Eye', 'Lambeth, London SE1 7PB, United Kingdom', 'https://www.example.com/images/london_eye.jpg'),
    (2.2945, 48.8584, 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 'https://www.example.com/images/eiffel_tower.jpg'),
    (-80.1918, 25.7617, 'The Freedom Tower', '600 Biscayne Blvd, Miami, FL 33132, USA', 'https://www.example.com/images/freedom_tower.jpg')
ON CONFLICT (name)
DO NOTHING;