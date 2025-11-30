-- Pre-populate petitions table with sample data
-- This script runs on application startup

-- Clear existing data (optional, useful for testing)
DELETE FROM votes;
DELETE FROM petitions;

-- Reset auto-increment counter
ALTER TABLE petitions ALTER COLUMN id RESTART WITH 1;

-- Insert sample petitions
INSERT INTO petitions (title, description) VALUES
('Improve Campus Wi-Fi Infrastructure', 'We request the university to upgrade the Wi-Fi network across all campus buildings. Current speeds are insufficient for online learning and research activities. Students frequently experience disconnections during important virtual lectures and exams.'),

('Extend Library Operating Hours', 'Petition to extend the library hours to 24/7 during exam periods and until midnight on regular days. Many students need quiet study spaces during late hours, and the current closing time of 10 PM is too early for effective exam preparation.'),

('Add More Vegetarian and Vegan Options in Cafeteria', 'We call for the introduction of more diverse vegetarian and vegan meal options in all campus cafeterias. Currently, the selection is very limited, and many students with dietary preferences or restrictions struggle to find suitable meals.'),

('Implement Campus-Wide Recycling Program', 'Request for a comprehensive recycling and waste management program across the entire campus. We need more recycling bins, better waste separation, and educational initiatives to promote environmental sustainability among students and staff.'),

('Expand Campus Sport Activities and Facilities', 'We request the university to expand sports facilities and introduce more diverse athletic programs. Students need access to modern gym equipment, more intramural sports leagues, and extended hours for recreational facilities. Physical fitness is essential for student health and academic performance.'),

('Establish Comprehensive Student Housing Reform Initiative', 'We, the undersigned students, faculty members, and concerned community members, hereby petition the university administration to undertake a comprehensive reform of student housing policies and facilities. The current state of student accommodation has reached a critical point that demands immediate attention and action.

CURRENT SITUATION: Our campus housing infrastructure has not kept pace with enrollment growth over the past decade. Many dormitories were built in the 1970s and 80s and have not received significant renovations since their construction. Students are living in overcrowded conditions, with many rooms designed for two occupants now housing three or four students. The lack of adequate study spaces, poor internet connectivity, insufficient heating and cooling systems, and outdated plumbing create an environment that is not conducive to academic success or personal well-being.

SPECIFIC CONCERNS: First, the overcrowding issue has become severe, with some students forced to live in converted lounges and common areas. This violates basic privacy rights and creates an uncomfortable living situation that negatively impacts mental health and academic performance. Second, maintenance response times are unacceptably long, often taking weeks to address critical issues such as broken heating systems during winter months or malfunctioning air conditioning during summer. Third, housing costs have increased by 35% over the past five years while the quality of facilities has declined, creating an unsustainable financial burden on students and their families.

PROPOSED SOLUTIONS: We propose a multi-phase approach to address these issues. Phase One should focus on immediate improvements: reducing room occupancy to design capacity, establishing a 24-hour emergency maintenance response system, and implementing a comprehensive facility assessment. Phase Two should involve medium-term renovations including updating HVAC systems, improving internet infrastructure, creating additional study spaces, and modernizing kitchen facilities in dormitories. Phase Three should encompass long-term planning for new housing construction to accommodate future enrollment growth and the establishment of a student housing advisory committee to ensure ongoing communication between residents and administration.

FINANCIAL CONSIDERATIONS: While we acknowledge that these improvements require significant financial investment, we believe they are essential for maintaining the university''s reputation and ensuring student success. We propose exploring multiple funding sources including alumni donations specifically designated for housing improvements, partnerships with private developers for new construction, and a transparent review of current housing fee allocation to ensure funds are being used effectively for facility maintenance and improvement.

EXPECTED OUTCOMES: Implementation of these reforms would result in improved student satisfaction, better academic outcomes due to enhanced living conditions, increased enrollment and retention rates, and a stronger reputation for the university. Other institutions that have undertaken similar housing reforms have seen measurable improvements in student well-being and academic performance.

We urge the administration to take immediate action on this critical issue. Students deserve safe, comfortable, and affordable housing that supports their educational goals. We request a formal response to this petition within 30 days and the formation of a task force including student representatives to begin addressing these concerns. The future success of our university depends on providing quality living conditions for all students.');

-- Add random votes to petitions (1-10 votes per petition)
-- Petition 1: Wi-Fi Infrastructure - 7 votes
INSERT INTO votes (email, name, http_session, timestamp, petition_id) VALUES
('john.doe@university.edu', 'John Doe', 'session001', CURRENT_TIMESTAMP, 1),
('sarah.smith@university.edu', 'Sarah Smith', 'session002', CURRENT_TIMESTAMP, 1),
('mike.jones@university.edu', 'Mike Jones', 'session003', CURRENT_TIMESTAMP, 1),
('emma.wilson@university.edu', 'Emma Wilson', 'session004', CURRENT_TIMESTAMP, 1),
('alex.brown@university.edu', 'Alex Brown', 'session005', CURRENT_TIMESTAMP, 1),
('lisa.taylor@university.edu', 'Lisa Taylor', 'session006', CURRENT_TIMESTAMP, 1),
('david.clark@university.edu', 'David Clark', 'session007', CURRENT_TIMESTAMP, 1);

-- Petition 2: Library Hours - 9 votes
INSERT INTO votes (email, name, http_session, timestamp, petition_id) VALUES
('olivia.martin@university.edu', 'Olivia Martin', 'session008', CURRENT_TIMESTAMP, 2),
('james.anderson@university.edu', 'James Anderson', 'session009', CURRENT_TIMESTAMP, 2),
('sophia.lee@university.edu', 'Sophia Lee', 'session010', CURRENT_TIMESTAMP, 2),
('william.white@university.edu', 'William White', 'session011', CURRENT_TIMESTAMP, 2),
('ava.garcia@university.edu', 'Ava Garcia', 'session012', CURRENT_TIMESTAMP, 2),
('robert.miller@university.edu', 'Robert Miller', 'session013', CURRENT_TIMESTAMP, 2),
('mia.davis@university.edu', 'Mia Davis', 'session014', CURRENT_TIMESTAMP, 2),
('benjamin.rodriguez@university.edu', 'Benjamin Rodriguez', 'session015', CURRENT_TIMESTAMP, 2),
('charlotte.martinez@university.edu', 'Charlotte Martinez', 'session016', CURRENT_TIMESTAMP, 2);

-- Petition 3: Vegetarian Options - 4 votes
INSERT INTO votes (email, name, http_session, timestamp, petition_id) VALUES
('lucas.hernandez@university.edu', 'Lucas Hernandez', 'session017', CURRENT_TIMESTAMP, 3),
('amelia.lopez@university.edu', 'Amelia Lopez', 'session018', CURRENT_TIMESTAMP, 3),
('henry.gonzalez@university.edu', 'Henry Gonzalez', 'session019', CURRENT_TIMESTAMP, 3),
('harper.wilson@university.edu', 'Harper Wilson', 'session020', CURRENT_TIMESTAMP, 3);

-- Petition 4: Recycling Program - 6 votes
INSERT INTO votes (email, name, http_session, timestamp, petition_id) VALUES
('ethan.moore@university.edu', 'Ethan Moore', 'session021', CURRENT_TIMESTAMP, 4),
('ella.jackson@university.edu', 'Ella Jackson', 'session022', CURRENT_TIMESTAMP, 4),
('alexander.thomas@university.edu', 'Alexander Thomas', 'session023', CURRENT_TIMESTAMP, 4),
('abigail.taylor@university.edu', 'Abigail Taylor', 'session024', CURRENT_TIMESTAMP, 4),
('daniel.lee@university.edu', 'Daniel Lee', 'session025', CURRENT_TIMESTAMP, 4),
('emily.harris@university.edu', 'Emily Harris', 'session026', CURRENT_TIMESTAMP, 4);

-- Petition 5: Sport Activities - 10 votes
INSERT INTO votes (email, name, http_session, timestamp, petition_id) VALUES
('matthew.clark@university.edu', 'Matthew Clark', 'session027', CURRENT_TIMESTAMP, 5),
('madison.lewis@university.edu', 'Madison Lewis', 'session028', CURRENT_TIMESTAMP, 5),
('joseph.walker@university.edu', 'Joseph Walker', 'session029', CURRENT_TIMESTAMP, 5),
('elizabeth.hall@university.edu', 'Elizabeth Hall', 'session030', CURRENT_TIMESTAMP, 5),
('samuel.allen@university.edu', 'Samuel Allen', 'session031', CURRENT_TIMESTAMP, 5),
('victoria.young@university.edu', 'Victoria Young', 'session032', CURRENT_TIMESTAMP, 5),
('jackson.king@university.edu', 'Jackson King', 'session033', CURRENT_TIMESTAMP, 5),
('scarlett.wright@university.edu', 'Scarlett Wright', 'session034', CURRENT_TIMESTAMP, 5),
('sebastian.lopez@university.edu', 'Sebastian Lopez', 'session035', CURRENT_TIMESTAMP, 5),
('grace.hill@university.edu', 'Grace Hill', 'session036', CURRENT_TIMESTAMP, 5);

-- Petition 6: Student Housing Reform - 8 votes
INSERT INTO votes (email, name, http_session, timestamp, petition_id) VALUES
('noah.scott@university.edu', 'Noah Scott', 'session037', CURRENT_TIMESTAMP, 6),
('emma.green@university.edu', 'Emma Green', 'session038', CURRENT_TIMESTAMP, 6),
('liam.baker@university.edu', 'Liam Baker', 'session039', CURRENT_TIMESTAMP, 6),
('sophia.adams@university.edu', 'Sophia Adams', 'session040', CURRENT_TIMESTAMP, 6),
('mason.nelson@university.edu', 'Mason Nelson', 'session041', CURRENT_TIMESTAMP, 6),
('isabella.carter@university.edu', 'Isabella Carter', 'session042', CURRENT_TIMESTAMP, 6),
('logan.mitchell@university.edu', 'Logan Mitchell', 'session043', CURRENT_TIMESTAMP, 6),
('ava.perez@university.edu', 'Ava Perez', 'session044', CURRENT_TIMESTAMP, 6);
