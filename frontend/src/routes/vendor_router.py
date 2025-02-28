from fastapi import APIRouter, Request
from src.config import templates

router = APIRouter(
    prefix="/vendor"
)

@router.get("/inventory")
async def index(request: Request):
    return templates.TemplateResponse("inventory.html", {'request': request})