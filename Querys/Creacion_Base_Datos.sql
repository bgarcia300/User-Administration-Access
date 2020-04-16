Xp_Create_Subdir 'C:\Proyecto Final\Logs'
GO
Xp_Create_Subdir 'C:\Proyecto Final\On_Primary'
GO

Create Database ProyectoFinal

ON PRIMARY
( Name = 'Proyecto.mdf', Filename = 'C:\proyecto Final\On_Primary\Proyecto.mdf',
Size = 10mb, MaxSize = 50mb, Filegrowth = 15% )

LOG ON
( Name = 'Proyecto.ldf', Filename = 'C:\proyecto Final\Logs\Proyecto.ldf',
Size = 5mb, MaxSize = 25mb, Filegrowth = 5mb)
GO

