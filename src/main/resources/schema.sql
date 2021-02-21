CREATE TABLE FoodItem (
    id         INTEGER PRIMARY KEY
                       UNIQUE
                       NOT NULL,
    name       STRING  NOT NULL,
    amount     INTEGER NOT NULL,
);
CREATE TABLE History (
    id         INTEGER  PRIMARY KEY
                        NOT NULL
                        UNIQUE,
    FoodItemId INTEGER  REFERENCES FoodItem
                        NOT NULL,
    useDate    DATETIME NOT NULL,
    amount     INTEGER  NOT NULL
);
CREATE TABLE Recipe (
    id   INTEGER PRIMARY KEY
                 NOT NULL,
    name STRING  NOT NULL
);
CREATE TABLE Ingredient (
    id       INTEGER PRIMARY KEY
                     NOT NULL
                     UNIQUE,
    name     STRING  NOT NULL,
    amount   INTEGER NOT NULL,
    recipeId         REFERENCES recipe (recipeId)
                     NOT NULL
);
