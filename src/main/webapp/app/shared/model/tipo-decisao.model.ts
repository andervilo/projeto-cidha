import { IProcesso } from 'app/shared/model/processo.model';

export interface ITipoDecisao {
  id?: number;
  descricao?: string;
  processos?: IProcesso[];
}

export const defaultValue: Readonly<ITipoDecisao> = {};
