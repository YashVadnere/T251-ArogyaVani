from fastapi import APIRouter, Request
from src.config import templates

router = APIRouter(
    prefix="/institute"
)

@router.get("/")
async def index(request: Request):
    return templates.TemplateResponse("institute.html", {"request": request})

@router.get("/requestform")
async def request_form(request: Request):
    return templates.TemplateResponse("requestform.html", {"request": request})