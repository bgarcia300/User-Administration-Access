Xp_Create_Subdir 'C:\Git\proyecto-final\Logs'
GO
Xp_Create_Subdir 'C:\Git\proyecto-final\On_Primary'
GO

Create Database ProyectoFinal

ON PRIMARY
( Name = 'Proyecto.mdf', Filename = 'C:\Git\proyecto-final\On_Primary\Proyecto.mdf',
Size = 10mb, MaxSize = 50mb, Filegrowth = 15% )

LOG ON
( Name = 'Proyecto.ldf', Filename = 'C:\Git\proyecto-final\Logs\Proyecto.ldf',
Size = 5mb, MaxSize = 25mb, Filegrowth = 5mb)
GO

