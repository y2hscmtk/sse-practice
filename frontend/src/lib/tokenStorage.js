const KEY = 'auth_token_bearer'

function get() {
  try {
    return localStorage.getItem(KEY)
  } catch {
    return null
  }
}

function set(bearer) {
  try {
    localStorage.setItem(KEY, bearer)
  } catch {
    // ignore
  }
}

function clear() {
  try {
    localStorage.removeItem(KEY)
  } catch {
    // ignore
  }
}

export const tokenStorage = { get, set, clear }

