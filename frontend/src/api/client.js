const BASE = import.meta.env.VITE_API_BASE?.replace(/\/$/, '') || ''

async function request(path, { method = 'GET', body, token } = {}) {
  const headers = new Headers()
  if (body !== undefined) headers.set('Content-Type', 'application/json')
  if (token) headers.set('Authorization', token)

  const res = await fetch(`${BASE}${path}`, {
    method,
    headers,
    body: body !== undefined ? JSON.stringify(body) : undefined,
    credentials: 'include',
  })

  const data = await res.json().catch(() => ({}))

  // API spec wraps all responses in { isSuccess, code, message, result }
  // Consider HTTP status as well, but prefer spec shape.
  if (!data || data.isSuccess === false) {
    const code = data?.code || `HTTP_${res.status}`
    const message = data?.message || res.statusText || '요청에 실패했습니다.'
    const error = new Error(`${code}: ${message}`)
    error.code = code
    error.payload = data
    throw error
  }

  return { res, data }
}

export const api = {
  baseUrl: BASE || '/',
  request,
}

