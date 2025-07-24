# ✅ Casos de Teste – MVP
**Projeto:** Marketplace de Artesanato Personalizado  
**Versão:** MVP  
**Data:** 17/07/2025  
**Cobertura:** Cadastro, login, produtos, orçamentos, avaliações e fluxo principal cliente-artesão

---

## 🧾 1. Cadastro de Usuário

| ID      | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|---------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT01-ok | Cadastro de pessoa física            | Dados válidos de PF                       | Conta criada com sucesso                   |
| CT02-ok | Cadastro de pessoa jurídica          | Dados válidos de PJ                       | Conta criada com sucesso                   |
| CT03-ok | Tentativa de cadastro com email repetido | Email já existente                   | Erro 400 – email já registrado             |
| CT04-ok | Cadastro sem campo obrigatório       | Dados faltando (ex: CPF, senha)           | Erro de validação                          |

---

## 🔐 2. Login

| ID      | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|---------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT05-ok | Login com credenciais corretas       | Email e senha válidos                     | Token JWT retornado                        |
| CT06-ok | Login com senha incorreta            | Email correto, senha inválida             | Erro 401 – credenciais inválidas           |

---

## 🎨 3. Cadastro de Artista

| ID      | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|---------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT07-ok | Cadastro de artista com usuário válido | ID de usuário e dados de bio/foto        | Artista vinculado com sucesso              |
| CT08-ok | Cadastro com usuário inexistente     | ID inválido                               | Erro 404 – usuário não encontrado          |
| CT09-ok | Cadastro com artista já existente    | ID já associado a artista                 | Erro 400 – artista já registrado           |

---

## 🛍️ 4. Cadastro de Produto

| ID      | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|---------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT10-ok | Cadastro de produto completo         | Nome, descrição, imagem, preço            | Produto salvo com sucesso                  |
| CT11-ok | Produto com categoria nova           | Categoria não existente                   | Nova categoria criada automaticamente      |
| CT12-ok | Produto sem nome                     | Nome vazio                                | Erro de validação                          |

---

## 🔎 5. Exploração de Produtos

| ID      | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|---------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT13-ok | Buscar lista de produtos             | Sem filtros                               | Lista com produtos ativos                  |
| CT14-ok | Buscar por categoria                 | Categoria específica                      | Lista filtrada com sucesso                 |

---

## 📝 6. Orçamentos

| ID       | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|----------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT150-ok | Enviar orçamento personalizado       | Cliente envia descrição                   | Orçamento criado e vinculado ao cliente    |
| CT16-ok  | Enviar orçamento baseado em produto  | Seleção de produto + descrição            | Orçamento vinculado ao produto             |
| CT17-ok  | Orçamento com campos obrigatórios faltando | Descrição vazia                       | Erro de validação                          |

---

## 📬 7. Resposta a Orçamentos

| ID      | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|---------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT18-ok | Artista responde orçamento           | Valor e mensagem                          | Status alterado para “respondido”          |
| CT19-ok | Artista responde orçamento que não é seu | ID inválido ou de outro artista        | Erro 403 – acesso negado                   |
| CT20-ok | Cliente aceita orçamento             | ID do orçamento                           | Status alterado para “aceito”              |

---

## ⭐ 8. Avaliações

| ID      | Caso de Teste                        | Entrada                                   | Resultado Esperado                         |
|---------|--------------------------------------|-------------------------------------------|--------------------------------------------|
| CT21-ok | Avaliação válida                     | Nota 1–5, comentário                      | Avaliação registrada                       |
| CT22-ok | Avaliação sem compra anterior        | Cliente sem vínculo com o produto         | Erro 403 – não autorizado                  |
| CT23-ok | Nota fora do intervalo               | Nota 0 ou 6                               | Erro de validação                          |

---

## 🛡️ 9. Regras de Acesso por Perfil

| ID     | Caso de Teste                        | Perfil               | Ação                                     | Resultado Esperado                     |
|--------|--------------------------------------|-----------------------|------------------------------------------|----------------------------------------|
| CT24   | CUSTOMER acessa rota de ARTISTA      | CUSTOMER              | `/artista/produtos`                      | Erro 403 – acesso negado               |
| CT25   | ARTISTA acessa rota de ADMIN         | ARTISTA               | `/admin/usuarios`                        | Erro 403 – acesso negado               |
| CT26   | ADMIN acessa rota protegida          | ADMIN                 | `/admin/usuarios`                        | Sucesso                                |

---

## 📦 10. Integração mínima recomendada para testes automáticos

- Testes de integração com `@SpringBootTest` e `@WithMockUser`
- Mock dos serviços de autenticação e repositórios
- Simulações de requisições com `MockMvc` ou `RestAssured`

---

## 🧪 Estratégia de Testes Complementar

- ✅ Testes manuais via Postman (CRUD, JWT, orçamentos)
- ✅ Testes automatizados de controladores (WebLayer)
- ✅ Cobertura de regras de negócio e segurança
