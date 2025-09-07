import { useState } from 'react'
import { auth } from '../api/auth'
import { tokenStorage } from '../lib/tokenStorage'

export default function ProfilePanel({ onLogout }) {
  const [me, setMe] = useState(null)
  const [loading, setLoading] = useState(false)
  const [status, setStatus] = useState('')
  const token = tokenStorage.get()

  function errorMessage(err) {
    if (!err) return '알 수 없는 오류'
    if (typeof err === 'string') return err
    if (err.message) return err.message
    try { return JSON.stringify(err) } catch { return '오류가 발생했습니다' }
  }

  async function handleFetchMe() {
    setLoading(true)
    setStatus('')
    try {
      const data = await auth.me()
      setMe(data)
      setStatus('프로필 조회 성공')
    } catch (err) {
      setMe(null)
      setStatus(errorMessage(err))
    } finally {
      setLoading(false)
    }
  }

  function handleLogout() {
    tokenStorage.clear()
    setMe(null)
    setStatus('로그아웃 되었습니다')
    onLogout?.()
  }

  return (
    <div className="panel">
      <div className="row">
        <span className="badge">인증됨</span>
        <code className="token">{token}</code>
      </div>
      <div className="actions">
        <button disabled={loading} onClick={handleFetchMe}>내 정보 조회</button>
        <button onClick={handleLogout} className="danger">로그아웃</button>
      </div>
      {me && (
        <pre className="result" aria-live="polite">{JSON.stringify(me, null, 2)}</pre>
      )}
      {status && <div className="status">{status}</div>}
    </div>
  )
}

