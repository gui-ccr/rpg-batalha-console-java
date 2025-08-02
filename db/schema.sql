CREATE TABLE herois (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    vida_maxima INTEGER NOT NULL,
    ataque INTEGER NOT NULL,
    defesa INTEGER NOT NULL,
    chance_critico INTEGER NOT NULL,
    multiplicador_critico REAL NOT NULL,
    chance_esquiva INTEGER NOT NULL,
    energia INTEGER NOT NULL,
    forca INTEGER NOT NULL,
    agilidade INTEGER NOT NULL,
    vigor INTEGER NOT NULL,
    presenca INTEGER NOT NULL,
    intelecto INTEGER NOT NULL,
    nivel INTEGER NOT NULL,
    experiencia_atual INTEGER NOT NULL,
    experiencia_para_proximo_nivel INTEGER NOT NULL
);