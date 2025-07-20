# 📋 Requisitos Não Funcionais – MVP
**Projeto:** Marketplace de Artesanato Personalizado  
**Versão:** MVP  
**Data:** 17/07/2025

---

## ✅ Requisitos por Categoria

| Categoria        | Requisito                                                                 |
|------------------|--------------------------------------------------------------------------|
| **Desempenho**   | O sistema deve responder às requisições REST em até **2 segundos** em 95% dos casos. |
|                  | As páginas públicas (produtos/artistas) devem carregar em até **3 segundos**. |
| **Escalabilidade** | A aplicação deve estar preparada para escalar horizontalmente via contêineres (ex: Docker, Kubernetes). |
| **Disponibilidade** | O sistema deve garantir pelo menos **99% de disponibilidade** mensal. |
| **Segurança**    | O acesso à API deve ser protegido por **JWT** e os endpoints autorizados por perfil de usuário (role). |
|                  | Senhas devem ser armazenadas com **hash seguro** (ex: bcrypt). |
|                  | Dados sensíveis (senhas, tokens) não devem ser expostos em logs ou mensagens de erro. |
| **Compatibilidade** | O frontend deve ser responsivo e funcionar em **navegadores modernos** (Chrome, Firefox, Safari, Edge). |
| **Usabilidade**  | A interface deve seguir boas práticas de UX e recomendações da **WCAG nível AA**. |
| **Internacionalização** | O sistema deve estar preparado para adicionar suporte multilíngue no futuro. |
| **Portabilidade** | O sistema deve ser distribuído em contêineres **Docker** e executável em diferentes ambientes (dev, staging, prod). |
| **Auditoria**    | As ações críticas (login, cadastro de produto, envio de orçamento) devem ser **logadas com timestamp e ID de usuário**. |
| **Backup**       | O banco de dados deve permitir **backups automáticos diários**. |
| **Observabilidade** | Logs devem estar estruturados (JSON ou padrão logstash) e a aplicação deve expor **métricas básicas** via endpoint (ex: Prometheus). |
| **Manutenibilidade** | O código-fonte deve seguir princípios de **Clean Architecture**, separação de responsabilidades e testes unitários. |
| **Documentação** | O backend deve expor documentação atualizada via **Swagger/OpenAPI**. |
| **Testabilidade** | A aplicação deve ter testes automatizados com cobertura mínima de **70%** para controladores e serviços principais. |

---

## 🛡️ Requisitos de Segurança Específicos

- [ ] Proteção contra ataques de força bruta via **rate limiting**
- [ ] Controle de **CORS** configurado apenas para domínios autorizados
- [ ] Validação de entradas para evitar **SQL Injection / XSS**
- [ ] Erros genéricos em produção (sem stack trace)
- [ ] Autorização por **role-based access control (RBAC)** via Spring Security

---

## 🎯 Observações

- Esses requisitos garantem a **qualidade não funcional mínima** do MVP, mas poderão ser evoluídos para requisitos SLOs e SLAs específicos na V2.
- A rastreabilidade dos requisitos deve ser mantida no GitHub por meio de issues e milestones.

