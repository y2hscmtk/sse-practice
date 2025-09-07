import { useEffect, useState } from 'react'
import { members } from '../api/members'

export default function UsersPage() {
  const [page, setPage] = useState(1)
  const [size, setSize] = useState(10)
  const [rows, setRows] = useState([])
  const [loading, setLoading] = useState(false)
  const [status, setStatus] = useState('')

  function errorMessage(err) {
    if (!err) return '알 수 없는 오류'
    if (typeof err === 'string') return err
    if (err.message) return err.message
    try { return JSON.stringify(err) } catch { return '오류가 발생했습니다' }
  }

  useEffect(() => {
    let ignore = false
    async function run() {
      setLoading(true)
      setStatus('')
      try {
        const list = await members.list({ page, size })
        if (!ignore) setRows(Array.isArray(list) ? list : [])
      } catch (err) {
        if (!ignore) {
          setRows([])
          setStatus(errorMessage(err))
        }
      } finally {
        if (!ignore) setLoading(false)
      }
    }
    run()
    return () => { ignore = true }
  }, [page, size])

  const hasPrev = page > 1
  const hasNext = rows.length >= size // heuristic when total count is unknown

  return (
    <div className="panel">
      <div className="actions" style={{ justifyContent: 'space-between', marginBottom: '0.5rem' }}>
        <div>
          <button onClick={() => setPage((p) => Math.max(1, p - 1))} disabled={!hasPrev || loading}>이전</button>
          <button onClick={() => setPage((p) => p + 1)} disabled={!hasNext || loading} className="primary" style={{ marginLeft: '0.5rem' }}>다음</button>
        </div>
        <div>
          <label htmlFor="size" style={{ marginRight: 8, color: 'var(--muted)' }}>페이지 크기</label>
          <select id="size" value={size} onChange={(e) => { setPage(1); setSize(Number(e.target.value)) }}>
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
            <option value={50}>50</option>
          </select>
        </div>
      </div>

      <div style={{ overflowX: 'auto' }}>
        <table className="table">
          <thead>
            <tr>
              <th style={{ textAlign: 'left' }}>Login ID</th>
              <th style={{ textAlign: 'left' }}>Authority</th>
            </tr>
          </thead>
          <tbody>
            {rows.length === 0 && !loading && (
              <tr>
                <td colSpan={2} style={{ color: 'var(--muted)' }}>데이터가 없습니다</td>
              </tr>
            )}
            {rows.map((u) => (
              <tr key={u.loginId}>
                <td><code className="token">{u.loginId}</code></td>
                <td>{u.authority}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {status && <div className="status">{status}</div>}
    </div>
  )
}

