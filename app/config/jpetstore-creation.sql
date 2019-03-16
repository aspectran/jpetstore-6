CREATE SCHEMA jpetstore;
RUNSCRIPT FROM 'classpath:jpetstore-schema.sql';
RUNSCRIPT FROM 'classpath:jpetstore-dataload.sql';