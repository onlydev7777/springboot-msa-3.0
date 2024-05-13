docker run -d -p 1521:1521 -p 81:81 -v ./h2:/opt/h2-data -e H2_OPTIONS="-ifNotExists" --network ecommerce-network --name=h2db oscarfonts/h2:2.2.224
