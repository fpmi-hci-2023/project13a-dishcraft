-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-10-03 09:01:10.86

-- tables
-- Table: comment
CREATE TABLE comment (
 comment_id int  NOT NULL,
 comment_text varchar(5000)  NOT NULL,
 comment_date timestamp  NOT NULL,
 user_id int  NOT NULL,
 recipe_id int  NOT NULL,
 CONSTRAINT comment_pk PRIMARY KEY (comment_id)
);

-- Table: favourite_recipe
CREATE TABLE favourite_recipe (
  recipe_id int  NOT NULL,
  user_id int  NOT NULL,
  save_date timestamp  NOT NULL,
  CONSTRAINT favourite_recipe_pk PRIMARY KEY (recipe_id,user_id)
);

-- Table: measure_unit
CREATE TABLE measure_unit (
  unit_id int  NOT NULL,
  unit_name varchar(50)  NOT NULL,
  CONSTRAINT measure_unit_pk PRIMARY KEY (unit_id)
);

-- Table: product
CREATE TABLE product (
     product_id int  NOT NULL,
     product_name varchar(50)  NOT NULL,
     image_path varchar(200)  NOT NULL,
     calories int  NOT NULL,
     proteins real  NOT NULL,
     fats real  NOT NULL,
     carbohydrates real  NOT NULL,
     CONSTRAINT product_pk PRIMARY KEY (product_id)
);

-- Table: rating
CREATE TABLE rating (
    rating_id int  NOT NULL,
    rating_value int  NOT NULL,
    user_id int  NOT NULL,
    recipe_id int  NOT NULL,
    CONSTRAINT rating_pk PRIMARY KEY (rating_id)
);

-- Table: recipe
CREATE TABLE recipe (
    recipe_id int  NOT NULL,
    recipe_name varchar(50)  NOT NULL,
    description varchar(2000)  NOT NULL,
    image_path varchar(200)  NOT NULL,
    cooking_time_minutes int  NOT NULL,
    ready_time_minutes int  NOT NULL,
    complexity_id int  NOT NULL,
    default_portions_number int  NOT NULL,
    author_user_id int  NOT NULL,
    CONSTRAINT recipe_pk PRIMARY KEY (recipe_id)
);

-- Table: recipe_complexity
CREATE TABLE recipe_complexity (
   complexity_id int  NOT NULL,
   complexity_name varchar(50)  NOT NULL,
   CONSTRAINT recipe_complexity_pk PRIMARY KEY (complexity_id)
);

-- Table: recipe_product
CREATE TABLE recipe_product (
    recipe_id int  NOT NULL,
    product_id int  NOT NULL,
    units_amount real  NOT NULL,
    unit_id int  NOT NULL,
    grams int  NOT NULL,
    CONSTRAINT recipe_product_pk PRIMARY KEY (recipe_id,product_id)
);

-- Table: step
CREATE TABLE step (
      step_id int  NOT NULL,
      step_description varchar(2000)  NOT NULL,
      image_path varchar(200)  NULL,
      number_in_recipe int  NOT NULL,
      recipe_id int  NOT NULL,
      CONSTRAINT step_pk PRIMARY KEY (step_id)
);

-- Table: user
CREATE TABLE "user" (
    user_id int  NOT NULL,
    user_name varchar(200)  NOT NULL,
    password varchar(200)  NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);

-- foreign keys
-- Reference: Step_Recipe (table: step)
ALTER TABLE step ADD CONSTRAINT Step_Recipe
    FOREIGN KEY (recipe_id)
        REFERENCES recipe (recipe_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: comment_recipe (table: comment)
ALTER TABLE comment ADD CONSTRAINT comment_recipe
    FOREIGN KEY (recipe_id)
        REFERENCES recipe (recipe_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: comment_user (table: comment)
ALTER TABLE comment ADD CONSTRAINT comment_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (user_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: favourite_recipe_recipe (table: favourite_recipe)
ALTER TABLE favourite_recipe ADD CONSTRAINT favourite_recipe_recipe
    FOREIGN KEY (recipe_id)
        REFERENCES recipe (recipe_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: favourite_recipe_user (table: favourite_recipe)
ALTER TABLE favourite_recipe ADD CONSTRAINT favourite_recipe_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (user_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: rating_recipe (table: rating)
ALTER TABLE rating ADD CONSTRAINT rating_recipe
    FOREIGN KEY (recipe_id)
        REFERENCES recipe (recipe_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: rating_user (table: rating)
ALTER TABLE rating ADD CONSTRAINT rating_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (user_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: recipe_product_product_unit (table: recipe_product)
ALTER TABLE recipe_product ADD CONSTRAINT recipe_product_product_unit
    FOREIGN KEY (unit_id)
        REFERENCES measure_unit (unit_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: recipe_products_products (table: recipe_product)
ALTER TABLE recipe_product ADD CONSTRAINT recipe_products_products
    FOREIGN KEY (product_id)
        REFERENCES product (product_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: recipe_products_recipes (table: recipe_product)
ALTER TABLE recipe_product ADD CONSTRAINT recipe_products_recipes
    FOREIGN KEY (recipe_id)
        REFERENCES recipe (recipe_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: recipe_recipe_complexity (table: recipe)
ALTER TABLE recipe ADD CONSTRAINT recipe_recipe_complexity
    FOREIGN KEY (complexity_id)
        REFERENCES recipe_complexity (complexity_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: recipe_user (table: recipe)
ALTER TABLE recipe ADD CONSTRAINT recipe_user
    FOREIGN KEY (author_user_id)
        REFERENCES "user" (user_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

