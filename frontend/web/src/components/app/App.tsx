import React from 'react'
import MapContainer from 'container/Map/MapContainer'
import { AuthProvider } from 'context/AuthContext/AuthProvider'

export const App = () => {
  return (
    <AuthProvider>
      <MapContainer />
    </AuthProvider>
  )
}
