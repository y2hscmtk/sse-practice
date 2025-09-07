import { useState } from 'react'
import { auth } from '../api/auth'

export default function AuthForm({ onSuccess }) {
  const [loginId, setLoginId] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [status, setStatus] = useState('')

  function errorMessage(err) {
    if (!err) return '알 수 없는 오류'
    if (typeof err === 'string') return err
    if (err.message) return err.message
    try { return JSON.stringify(err) } catch { return '오류가 발생했습니다' }
  }

  async function handleJoin(e) {
    e?.preventDefault()
    setLoading(true)
    setStatus('')
    try {
      await auth.join({ loginId, password })
      setStatus('회원가입 성공')
      setLoginId('')
      setPassword('')
      onSuccess?.()
    } catch (err) {
      setStatus(errorMessage(err))
    } finally {
      setLoading(false)
    }
  }

  async function handleLogin(e) {
    e?.preventDefault()
    setLoading(true)
    setStatus('')
    try {
      await auth.login({ loginId, password })
      setStatus('로그인 성공')
      setLoginId('')
      setPassword('')
      onSuccess?.()
    } catch (err) {
      setStatus(errorMessage(err))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="panel">
      <form onSubmit={(e) => e.preventDefault()}>
        <div className="field">
          <label htmlFor="loginId">아이디</label>
          <input
            id="loginId"
            value={loginId}
            onChange={(e) => setLoginId(e.target.value)}
            placeholder="user123"
            autoComplete="username"
          />
        </div>
        <div className="field">
          <label htmlFor="password">비밀번호</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="password123!"
            autoComplete="current-password"
          />
        </div>
        <div className="actions">
          <button disabled={loading} onClick={handleJoin}>회원가입</button>
          <button disabled={loading} onClick={handleLogin} className="primary">로그인</button>
        </div>
      </form>
      {status && <div className="status">{status}</div>}
    </div>
  )
}

