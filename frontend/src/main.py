from fastapi import FastAPI
from src.routes import index_router, auth_router, institute_router, vendor_router
from fastapi.middleware.cors import CORSMiddleware
from fastapi.templating import Jinja2Templates
from fastapi.staticfiles import StaticFiles

app = FastAPI()

origins = [
    "http://localhost:8000"
]

app.mount("/static", StaticFiles(directory="src/static"), name="static")

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

app.include_router(index_router.router)
app.include_router(auth_router.router)
app.include_router(institute_router.router)
app.include_router(vendor_router.router)
