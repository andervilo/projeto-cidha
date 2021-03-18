import { IComarca } from 'app/shared/model/comarca.model';
import { IQuilombo } from 'app/shared/model/quilombo.model';

export interface IProcesso {
  id?: number;
  oficio?: string;
  assunto?: any;
  linkUnico?: string;
  secaoJudiciaria?: string;
  comarcas?: IComarca[];
  quilombos?: IQuilombo[];
}

export const defaultValue: Readonly<IProcesso> = {};
