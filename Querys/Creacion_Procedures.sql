use ProyectoFinal
go

create procedure ProcedureInsercionUsuario
(

@idRol int
, @nombreCompleto nvarchar(30)
, @telefono int 
, @funcion nvarchar(40)
, @fecha_Nacimiento date
, @observaciones nvarchar(500) = 'No hay observaciones'
)
as
begin Try 
		begin Transaction InsercionUsuario

			
			insert into Usuarios (Id_Rol,Nombre_completo_Usuario, Telefono, Funcion, Fecha_Nacimiento) 
			values (@idRol,@nombreCompleto, @telefono, @funcion, @fecha_Nacimiento)

			declare @rolIngresado nvarchar(15) = (select Nombre_Rol from Roles where Roles.Id_Rol = @idRol)
			declare @idUsuarioIngresado int = (select Id_Usuario from Usuarios where Nombre_Completo_Usuario = @nombreCompleto)

				if	(@rolIngresado = 'Director' or @rolIngresado = 'Administrador' or @rolIngresado = 'Usuario')
					if	(@rolIngresado = 'Director')
							insert into Directores(Id_Rol, Id_Usuario, Nombre_Completo_Director, Observaciones) 
							values (@idRol, @idUsuarioIngresado, @nombreCompleto, @observaciones)
						else
					if (@rolIngresado = 'Administrador')
							insert into Administradores(Id_Rol, Id_Usuario, Nombre_Completo_Administrador, Observaciones) 
							values (@idRol, @idUsuarioIngresado, @nombreCompleto, @observaciones)
					else 
						print 'El usuario se ingresó a la tabla usuario'
	
		commit Transaction InsercionUsuario

	End try 
	Begin Catch 

		select 'El rol ingresado no existe. ' + ERROR_MESSAGE()
		Rollback Transaction InsercionUsuario

	End catch 
go

