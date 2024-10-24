# Instalar modulos de node para el proyecto en web
npm install "dentro de la carpeta frontend/web"
# Generar tsconfig
npx tsc --init

> [!CAUTION]
> Tener en cuenta estas instalaciones en caso de aparecer alguno de estos errores

### ERRORS ###
# Resolver error ""Could not find a declaration file for module 'react'.""
npm i --save-dev @types/react 
# Resolver error ""Cannot find module '@testing-library/react'""
npm i --save-dev @types/jest
npm i --save-dev @types/react-dom
npm i --save-dev @testing-library/react
npm i --save-dev @testing-library/jest-dom    

> [!TIP]
> Ejecucion de proyecto npm
# Run
npm start

# Instalar modulos de node para el proyecto en mobile
npm install "dentro de la carpeta frontend/mobile"

> [!TIP]
> Ejecucion mobile, instalacion dentro de mobile
> npx create-expo-app@latest -> Crear app
> npx expo start --tunnel

