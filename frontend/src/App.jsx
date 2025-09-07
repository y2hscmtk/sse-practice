import { useMemo, useState } from 'react'
import './index.css'
import { api } from './api/client'
import { tokenStorage } from './lib/tokenStorage'
import AuthForm from './components/AuthForm'
import ProfilePanel from './components/ProfilePanel'
import UsersPage from './components/UsersPage'

function App() {
  const token = tokenStorage.get()
  const isAuthed = useMemo(() => Boolean(token), [token])
  const [page, setPage] = useState(isAuthed ? 'users' : 'auth')

  const Nav = (
    <div className="actions" style={{ marginBottom: '1rem' }}>
      <button onClick={() => setPage('auth')} disabled={page === 'auth'}>Auth</button>
      <button onClick={() => setPage('profile')} disabled={page === 'profile'}>Profile</button>
      <button onClick={() => setPage('users')} disabled={page === 'users'}>Users</button>
    </div>
  )

  return (
    <div className="container">
      <h1>SSE-Practice Auth Demo</h1>
      {Nav}
      {page === 'auth' && <AuthForm onSuccess={() => setPage('users')} />}
      {page === 'profile' && <ProfilePanel onLogout={() => setPage('auth')} />}
      {page === 'users' && <UsersPage />}
      <footer>
        <small>API Base: {api.baseUrl}</small>
      </footer>
    </div>
  )
}

export default App
