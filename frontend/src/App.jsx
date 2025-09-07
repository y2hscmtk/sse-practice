import { useMemo, useState } from 'react'
import './index.css'
import { api } from './api/client'
import { tokenStorage } from './lib/tokenStorage'
import AuthForm from './components/AuthForm'
import ProfilePanel from './components/ProfilePanel'

function App() {
  const token = tokenStorage.get()
  const isAuthed = useMemo(() => Boolean(token), [token])
  const [page, setPage] = useState(isAuthed ? 'profile' : 'auth')

  const Nav = (
    <div className="actions" style={{ marginBottom: '1rem' }}>
      <button onClick={() => setPage('auth')} disabled={page === 'auth'}>Auth</button>
      <button onClick={() => setPage('profile')} disabled={page === 'profile'}>Profile</button>
    </div>
  )

  return (
    <div className="container">
      <h1>SSE-Practice Auth Demo</h1>
      {Nav}
      {page === 'auth' && <AuthForm onSuccess={() => setPage('profile')} />}
      {page === 'profile' && <ProfilePanel onLogout={() => setPage('auth')} />}
      <footer>
        <small>API Base: {api.baseUrl}</small>
      </footer>
    </div>
  )
}

export default App
