CREATE TABLE tb_artist_social_media (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    instagram VARCHAR(255),
    facebook VARCHAR(255),
    twitter_x VARCHAR(255),
    linkedin VARCHAR(255),
    tiktok varchar(255),
    youtube VARCHAR(255),
    pinterest VARCHAR(255),
    telegram VARCHAR(255),
    website VARCHAR(255),
    artist_id BIGINT NOT NULL,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (artist_id) references tb_artist(id)
 );

