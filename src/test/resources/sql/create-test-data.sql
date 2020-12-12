INSERT INTO users(id, email, image, login_id, name, provider) VALUES
(1, 'testIntegration@test.com', 'image', 'testIntegration', 'Integration Test', 'github');

INSERT INTO responses(id, repository_url, commits_count, open_pull_request_count, readme_url, readme_content) VALUES
(1, 'url1', 2, 3, 'readmeurl1', 'readmecontent1'),
(2, 'url12', 2, 3, 'readmeurl1', 'readmecontent1'),
(3, 'url3', 2, 3, 'readmeurl1', 'readmecontent1'),
(4, 'url4', 2, 3, 'readmeurl1', 'readmecontent1'),
(5, 'url5', 2, 3, 'readmeurl1', 'readmecontent1'),
(6, 'url6', 2, 3, 'readmeurl1', 'readmecontent1'),
(7, 'url7', 2, 3, 'readmeurl1', 'readmecontent1'),
(8, 'url8', 2, 3, 'readmeurl1', 'readmecontent1'),
(9, 'url9', 2, 3, 'readmeurl1', 'readmecontent1'),
(10, 'url10', 2, 3, 'readmeurl1', 'readmecontent1'),
(11, 'url11', 2, 3, 'readmeurl1', 'readmecontent1');

INSERT INTO analysis_history(id, login_id, owner_name, repository_name, searched_on, response_id) VALUES
(1, 'testIntegration', 'test', 'testRepo', '2020-01-01', 1),
(2, 'testIntegration', 'test', 'testRepo', '2020-01-01', 2),
(3, 'testIntegration', 'test', 'testRepo', '2020-01-01', 3),
(4, 'testIntegration', 'test', 'testRepo', '2020-01-01', 4),
(5, 'testIntegration', 'test', 'testRepo', '2020-01-01', 5),
(6, 'testIntegration', 'test', 'testRepo', '2020-01-01', 6),
(7, 'testIntegration', 'test', 'testRepo', '2020-01-05', 7),
(8, 'testIntegration', 'test', 'testRepo', '2020-01-01', 8),
(9, 'testIntegration', 'test', 'testRepo', '2020-01-01', 9),
(10, 'testIntegration', 'test', 'testRepo', '2020-01-01', 10),
(11, 'testIntegration', 'test', 'testRepo', '2020-01-01', 11);

