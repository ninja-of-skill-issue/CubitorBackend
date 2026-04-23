// --- Data Management for Cubing App ---
// This file is the SINGLE SOURCE OF TRUTH for all persistence.
// All reads and writes to localStorage happen here only.

// Multi-session State
let currentSession = '3x3';
let allSessions = {
    '3x3': [],
    '2x2': [],
    '4x4': [],
    '5x5': [],
    '6x6': [],
    '7x7': [],
    '3x3oh': [],
    '3x3bld': [],
    '4x4bld': [],
    '5x5bld': [],
    'mbld': [],
    'clock': [],
    'megaminx': [],
    'pyraminx': [],
    'skewb': [],
    'sq1': []
};

let solveHistory = []; // Points to current session's array

const wcaEvents = [
    { id: '3x3', name: '3x3x3', icon: '3' },
    { id: '2x2', name: '2x2x2', icon: '2' },
    { id: '4x4', name: '4x4x4', icon: '4' },
    { id: '5x5', name: '5x5x5', icon: '5' },
    { id: '6x6', name: '6x6x6', icon: '6' },
    { id: '7x7', name: '7x7x7', icon: '7' },
    { id: '3x3oh', name: '3x3 OH', icon: 'OH' },
    { id: '3x3bld', name: '3x3 BLD', icon: 'BLD' },
    { id: '4x4bld', name: '4x4 BLD', icon: '4B' },
    { id: '5x5bld', name: '5x5 BLD', icon: '5B' },
    { id: 'clock', name: 'Clock', icon: 'CL' },
    { id: 'megaminx', name: 'Megaminx', icon: 'MG' },
    { id: 'pyraminx', name: 'Pyraminx', icon: 'PY' },
    { id: 'skewb', name: 'Skewb', icon: 'SK' },
    { id: 'sq1', name: 'Square-1', icon: 'S1' }
];

// ─── SAVE ────────────────────────────────────────────────────────────────────
// Saves all app state: sessions, current session, theme, and font.
function saveToLocalStorage() {
    localStorage.setItem('allSessions', JSON.stringify(allSessions));
    localStorage.setItem('currentSession', currentSession);

    // Derive theme from body classes
    const theme = document.body.classList.contains('theme-blue')  ? 'blue'  :
                  document.body.classList.contains('theme-green') ? 'green' :
                  document.body.classList.contains('theme-white') ? 'white' : 'black';
    localStorage.setItem('userTheme', theme);

    // Derive font from body classes
    const font = document.body.classList.contains('font-elegant') ? 'elegant' :
                 document.body.classList.contains('font-tech')    ? 'tech'    :
                 document.body.classList.contains('font-mono')    ? 'mono'    : 'default';
    localStorage.setItem('userFont', font);
}

// ─── LOAD ────────────────────────────────────────────────────────────────────
// Loads all solve session data from localStorage.
function loadFromLocalStorage() {
    const savedSessions = localStorage.getItem('allSessions');
    if (savedSessions) {
        try {
            allSessions = JSON.parse(savedSessions);
        } catch (e) {
            console.warn('Failed to parse allSessions from localStorage:', e);
        }
    }

    const savedCurrent = localStorage.getItem('currentSession');
    if (savedCurrent && allSessions[savedCurrent]) {
        currentSession = savedCurrent;
    }

    solveHistory = allSessions[currentSession];
}

// Reads saved theme from localStorage and applies it to the DOM.
// Calls setTheme() which is defined in main_page.html.
function loadTheme() {
    const savedTheme = localStorage.getItem('userTheme') || 'black';
    if (typeof setTheme === 'function') {
        setTheme(savedTheme, null);
    }
}

// Reads saved font from localStorage and applies it to the DOM.
// Calls setFont() which is defined in main_page.html.
function loadFont() {
    const savedFont = localStorage.getItem('userFont') || 'default';
    if (typeof setFont === 'function') {
        setFont(savedFont, null);
    }
}
