from fastapi import FastAPI
from app.fase1 import router as fase1_router
from app.fase2 import router as fase2_router
from app.fase3 import router as fase3_router

app = FastAPI(title="Nexus Match")

app.include_router(fase1_router, prefix="/fase1", tags=["Fase 1"])
app.include_router(fase2_router, prefix="/fase2", tags=["Fase 2"])
app.include_router(fase3_router, prefix="/fase3", tags=["Fase 3"])