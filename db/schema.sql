-- Alterado para commit separado em 18/08/2025
CREATE TABLE herois (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL UNIQUE,
    vida_atual INTEGER NOT NULL,
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

CREATE TABLE inventario_itens (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    heroi_id INTEGER,
    nome TEXT NOT NULL,
    descricao TEXT NOT NULL,
    tipo_item TEXT NOT NULL, -- Ex: Pocao, Espada
    valor_numerico INTEGER NOT NULL, -- Ex: cura da poção, dano da espada
    FOREIGN KEY (heroi_id) REFERENCES herois(id) ON DELETE CASCADE
);