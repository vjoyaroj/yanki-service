# yanki-service

## Descripcion
Microservicio de billetera movil Yanki para monederos, recargas, retiros y pagos P2P.

## Endpoints
- `GET /api/v1/yanki/wallets`
- `POST /api/v1/yanki/wallets`
- `GET /api/v1/yanki/wallets/{id}`
- `GET /api/v1/yanki/wallets/by-phone/{phoneNumber}`
- `POST /api/v1/yanki/wallets/{walletId}/link-debit-card`
- `POST /api/v1/yanki/wallets/{walletId}/unlink-debit-card`
- `POST /api/v1/yanki/topups`
- `POST /api/v1/yanki/withdrawals`
- `POST /api/v1/yanki/payments`

## Nota
El `docker-compose.yml` del entorno esta en este repositorio (`yanki-service`).

## Proyectos relacionados
- https://github.com/vjoyaroj/bank-config-repo
- https://github.com/vjoyaroj/microservices-config
- https://github.com/vjoyaroj/eureka-server
- https://github.com/vjoyaroj/yanki-service
- https://github.com/vjoyaroj/api-gateway
- https://github.com/vjoyaroj/transactions-service
- https://github.com/vjoyaroj/debit-cards-service
- https://github.com/vjoyaroj/customer-service
- https://github.com/vjoyaroj/credits-service
- https://github.com/vjoyaroj/accounts-service
